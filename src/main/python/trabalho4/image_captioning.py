import numpy as np
import matplotlib.pyplot as plt
from tensorflow import keras
import joblib

plt.style.use('seaborn-darkgrid')


def load_vocab():
    word_to_index = joblib.load('word_to_index.dump')
    index_to_word = joblib.load('index_to_word.dump')
    return word_to_index, index_to_word


def load_vgg19():
    """
    Loads the VGG19 model for feature extraction
    :return: VGG19 model with features as output
    """
    return keras.models.load_model('vgg19.h5')


def load_image(image_path):
    """
    Processes and image for the VGG19 model
    :param image_path: string with the path to the image
    :img : numpy array with the preprocessd image to be fed to the VGG19 model
    """
    img = keras.preprocessing.image.load_img(image_path, target_size=(224, 224))
    img = keras.preprocessing.image.img_to_array(img)
    img = img.reshape((1, img.shape[0], img.shape[1], img.shape[2]))
    img = keras.applications.vgg19.preprocess_input(img)
    return img


def load_model():
    return keras.models.load_model('model.h5')


def generate_caption(model, word_to_index, index_to_word, features, max_length):
    """
    Generates a caption fot the image using a greedy aproach
    :param model: keras model to predict the caption
    :param word_to_index: dictionary with vocabulary mapping string to integer
    :param index_to_word: dictionary with vocabulary mapping integer to string
    :param features: array with VGG19 features for the image
    :param max_length: integer with maximum length for a caption
    :return : string with caption
    """
    caption = '<start>'

    for i in range(max_length):

        sequence = [word_to_index[word] for word in caption.split(' ') if word in word_to_index]

        sequence = keras.preprocessing.sequence.pad_sequences([sequence], max_length)

        next_word = model.predict(([[features[0]], [sequence[0]]]), verbose=0)

        next_word = np.argmax(next_word)

        word = index_to_word[next_word]

        if word is None:
            word += ' <end>'
            break

        caption += ' ' + word

        if word == '<end>':
            break

    caption = caption.split(' ')
    caption = caption[1:-1]
    caption = ' '.join(caption)
    return caption


def test_model(model, vgg19, word_to_index, index_to_word, max_length, image):
    """
    Selects and image, generates a caption, saves and shows it
    :param model: keras model to predict the caption
    :param vgg19: keras VGG19 model for feature extraction
    :param word_to_index: dictionary with vocabulary mapping string to integer
    :param index_to_word: dictionary with vocabulary mapping integer to string
    :param max_length: integer with max size of caption
    :param image: image for caption prediction
    """

    plt.figure(figsize=(10, 10))

    im = plt.imread(image)
    plt.imshow(im)

    features = load_image(image)
    features = vgg19.predict(features, verbose=0)

    plt.title(generate_caption(model, word_to_index, index_to_word, features, max_length), size=25)
    plt.grid(False)
    plt.xticks([])
    plt.yticks([])

    plt.savefig('{}/response.jpg'.format(image.split('/')[0]))


def caption_image(image):
    vgg19 = load_vgg19()
    model = load_model()
    word_to_index, index_to_word = load_vocab()
    max_length = 31
    test_model(model, vgg19, word_to_index, index_to_word, max_length, image)


if __name__ == "__main__":
    caption_image(input('> '))
