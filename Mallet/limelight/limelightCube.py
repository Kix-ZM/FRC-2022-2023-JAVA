import cv2
import numpy as np

# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    img_threshold = cv2.inRange(img_hsv, (107, 97, 105), (131, 255, 255))
    contours, _ = cv2.findContours(img_threshold,
                                   cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    largestContour = np.array([[]])
    llpython = [0, 0, 0, 0, 0, 0, 0, 0]
    cX = 160
    cY = 120
    if len(contours) > 0:
        cv2.drawContours(image, contours, -1, 255, 2)
        largestContour = max(contours, key=cv2.contourArea)

        if cv2.contourArea(largestContour) > 50:
            M = cv2.moments(largestContour)
            if M["m00"] != 0:
                cX = int(M["m10"] / M["m00"])
                cY = int(M["m01"] / M["m00"])
    ctr = np.array([[cX - 2, cY + 2], [cX + 2, cY + 2], [cX + 2, cY - 2], [cX - 2, cY - 2]]).reshape((-1, 1, 2)).astype(
        np.int32)
    llpython = [0, 0, 0, 0, 0, 0, 0, 0]

    # make sure to return a contour,
    # an image to stream,
    # and optionally an array of up to 8 values for the "llpython"
    # networktables array
    return ctr, image, llpython