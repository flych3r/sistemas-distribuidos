from socket import *
import base64
import json
import os
import shutil
import io
from semantic_image import SemanticImage

serverPort = 12000
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('', serverPort))
serverSocket.listen(1)


def request(conn_socket):
    data = io.BytesIO()
    buffer = conn_socket.recv(1024)
    data.write(buffer)

    begin = -1
    end = -1

    # verificação para encontrar o tamanho total dos dados que serão enviados
    print('Receiving data')
    i = 0
    while i <= len(buffer):
        if begin != (-1) and end != (-1):
            break
        if data.getvalue()[2:].decode()[i] == ":" and begin == -1:
            begin = i + 1
        elif data.getvalue()[2:].decode()[i] == ":" and end == -1:
            end = i
        i = i + 1

    # identifica o tamanho da mensagem
    data_size = int(data.getvalue()[2:].decode()[begin:end]) + end + 3
    print('Data size =', data_size)

    # lê enquanto a mensagem está no buffer
    data_size -= len(buffer)
    while data_size > 0:
        buffer = conn_socket.recv(data_size)
        data.write(buffer)
        data_size -= len(buffer)

    # carrega o json da mensagem
    data = json.loads(data.getvalue()[end + 3:].decode())
    print("Data received successfully!")
    return data


def response(data, conn_socket, address):
    # verifica a integridade da mensagem
    if data['messageType'] != 0 or data['objectReference'] != 'SemanticImage' or data['methodId'] not in [1, 2]:
        print('Invalid message!')
        return 'invalid'

    # instancia o objeto
    semantic_image = SemanticImage()
    os.mkdir('{}'.format(addr))

    # lê a imagem codificada no json
    image = base64.b64decode(data['arguments'])
    request_filename = '{}/request.jpg'.format(address)

    with open(request_filename, 'wb') as f:
        f.write(image)

    # método para legendar imagem
    if data['methodId'] == 1:
        print('Captioning image')
        semantic_image.caption_image(request_filename)
        print('Finished captioning')
    # método para detectar objetos na imagem
    if data['methodId'] == 2:
        print('Detecting objects in image')
        semantic_image.detect_objects(request_filename)
        print('Finished objects detection')

    # muda o tipo de mensagem para resposta
    data['messageType'] = 1

    response_filename = '{}/response.jpg'.format(address)
    # codifica imagem e guarda no json
    with open(response_filename, 'rb') as f:
        data['arguments'] = base64.b64encode(f.read()).decode()

    shutil.rmtree('{}'.format(addr))

    # responda ao cliente
    conn_socket.send((json.dumps(data) + "\r\n").encode())
    print('Response sent!')


print('The server is ready to receive')
while True:
    # cria o socket pro cliente e estabelece a conexão
    connection_socket, addr = serverSocket.accept()

    # captura a requisição do cliente
    req = request(connection_socket)

    # envia a resposta ao cliente
    response(req, connection_socket, addr)

    connection_socket.close()
