from image_captioning import caption_image
from object_detection import detect_objects


class SemanticImage:
    def __init__(self):
        pass

    @staticmethod
    def caption_image(request_filename):
        """
        Chama o método para legendar uma imagem
        :param request_filename: nome do arquivo da imagem
        :return: imagem legendada
        """
        caption_image(request_filename)

    @staticmethod
    def detect_objects(request_filename):
        """
        Chama o método para detectar objetos em uma imagem
        :param request_filename: nome o arquivo da imagem
        :return: imagem com seus objetos detectados
        """
        detect_objects(request_filename)
