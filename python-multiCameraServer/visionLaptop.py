# import the opencv library
import cv2
import numpy as np

# define a video capture object
# https://www.reddit.com/r/learnpython/comments/oxo3gd/why_do_i_have_to_use_cv2cap_dshow/
vid = cv2.VideoCapture(0, cv2.CAP_DSHOW)
vid.set(3, 640)
vid.set(4, 480)

# preallocate memory
orig_img = np.zeros(shape=(640, 480, 3), dtype=np.uint8)
while True:
    # start_time = time.time()

    # Capture the video frame
    # by frame
    ret, orig_img = vid.read()
    output_img = np.copy(orig_img)

    hsv_img = cv2.cvtColor(orig_img, cv2.COLOR_BGR2HSV)

    # from hsv calculator using
    yellow_mask = cv2.inRange(hsv_img, (18, 71, 167), (39, 255, 255))

    contour_list, hierarchy = cv2.findContours(yellow_mask, mode=cv2.RETR_EXTERNAL, method=cv2.CHAIN_APPROX_SIMPLE)
    if len(contour_list) != 0:
        contour = max(contour_list, key=cv2.contourArea)
        # Ignore small contours that could be because of noise/bad thresholding
        if cv2.contourArea(contour) < 1000:
            continue

        cv2.drawContours(output_img, contour, -1, color=(255, 255, 255), thickness=-1)

        M = cv2.moments(contour)
        if M["m00"] != 0:
            cX = int(M["m10"] / M["m00"])
            cY = int(M["m01"] / M["m00"])
        else:
            continue

        rect = cv2.minAreaRect(contour)
        # center, size, angle = rect
        # center = tuple([int(dim) for dim in center])  # Convert to int so we can draw

        # Draw rectangle and circle
        cv2.drawContours(output_img, [cv2.boxPoints(rect).astype(int)], -1, color=(0, 0, 255), thickness=2)
        cv2.circle(output_img, center=(cX, cY), radius=3, color=(0, 0, 255), thickness=-1)

        # top left is 0,0
        print(cX, cY)

    # calc fps
    # fps = 1 / (time.time() - start_time)
    # cv2.putText(output_img, str(round(fps, 1)), (0, 40), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255))

    # Display the resulting frame
    cv2.imshow('frame', output_img)

    # the 'q' button is set as the
    # quitting button you may use any
    # desired button of your choice
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# After the loop release the cap object
vid.release()
# Destroy all the windows
cv2.destroyAllWindows()
