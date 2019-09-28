import cv2
import matplotlib.pyplot as plt
import numpy as np
import pytesseract as pyt
from skimage import filters, io
from skimage.util import invert

#
#plt.imshow(img)
#plt.show()
#print(img)
#
##gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
##plt.imshow(gray)
##plt.show()
##print(gray)
##img = cv2.medianBlur(img,5)
##mid = cv2.GaussianBlur(image,(5,5),0)
#th3 = cv2.adaptiveThreshold(img,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,cv2.THRESH_BINARY,11,2)
#plt.imshow(th3)
#plt.show()  
#print(th3)
#
#output = pyt.image_to_string(th3)
#print(output)

pic = 'X00016469672.jpg'
#im = cv2.imread(pic)
#gray = cv2.threshold(im, 0, 255,
#		cv2.THRESH_BINARY | cv2.THRESH_OTSU)[1]
#plt.imshow(gray)
#plt.show()
#img = cv2.imread('bigcornell.jpg',0)
#img = cv2.medianBlur(img,5)
#
#ret,th1 = cv2.threshold(img,127,255,cv2.THRESH_BINARY)
#th2 = cv2.adaptiveThreshold(img,255,cv2.ADAPTIVE_THRESH_MEAN_C,\
#            cv2.THRESH_BINARY,11,2)
#th3 = cv2.adaptiveThreshold(img,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,\
#            cv2.THRESH_BINARY,11,2)
#
#titles = ['Original Image', 'Global Thresholding (v = 127)',
#            'Adaptive Mean Thresholding', 'Adaptive Gaussian Thresholding']
#images = [img, th1, th2, th3]
#
#for i in range(4):
#    plt.subplot(2,2,i+1),plt.imshow(images[i],'gray')
#    plt.title(titles[i])
#    plt.xticks([]),plt.yticks([])
#plt.show()
#
#
#
#fig, ax = plt.subplots(nrows=2, ncols=2)
#
#image = cv2.imread(pic)
#gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
#edges = filters.sobel(gray)
#
#low = 0.1
#high = 0.35
#
#lowt = (edges > low).astype(int)
#hight = (edges > high).astype(int)
#hyst = filters.apply_hysteresis_threshold(edges, low, high)
#
#ax[1, 0].imshow(lowt, cmap='magma')
#ax[1, 0].set_title('Low threshold')
#
#for a in ax.ravel():
#    a.axis('off')
#
#plt.tight_layout()
#plt.show()
#
#
#
##im = cv2.imread('bigcornell.jpg',0)
###edge = invert(filters.sobel(im))
##edge = filters.sobel(im)
##io.imshow(edge)
##io.show()
#
#
#output = pyt.image_to_string(lowt)
#print(output)

import cv2
#import numpy as np
from matplotlib import pyplot as plt

img = cv2.imread('X00016469672.jpg',1)
img = cv2.medianBlur(img,5)
plt.imshow(img)
plt.show()
ret,th1 = cv2.threshold(img,127,255,cv2.THRESH_BINARY)
th2 = cv2.adaptiveThreshold(img,255,cv2.ADAPTIVE_THRESH_MEAN_C,\
            cv2.THRESH_BINARY,11,2)
th3 = cv2.adaptiveThreshold(img,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,\
            cv2.THRESH_BINARY,11,2)

titles = ['Original Image', 'Global Thresholding (v = 127)',
            'Adaptive Mean Thresholding', 'Adaptive Gaussian Thresholding']
images = [img, th1, th2, th3]
#
#for i in range(4):
#    plt.subplot(2,2,i+1),plt.imshow(images[i],'gray')
#    plt.title(titles[i])
#    plt.xticks([]),plt.yticks([])
#plt.show()

#output = pyt.image_to_string(th1)
#print(output)
