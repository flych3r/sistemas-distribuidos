import cv2
import matplotlib.pyplot as plt
import cvlib as cv
from cvlib.object_detection import draw_bbox


def detect_objects(image):
    im = cv2.imread(image)
    bbox, label, conf = cv.detect_common_objects(im)
    output_image = draw_bbox(im, bbox, label, conf)
    output_image = cv2.cvtColor(output_image, cv2.COLOR_BGR2RGB)

    plt.imsave('{}/response.jpg'.format(image.split('/')[0]), output_image)

