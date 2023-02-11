import json
import time

import cv2
import numpy as np
from cscore import CameraServer
from ntcore import NetworkTableInstance

def main():
    with open('/boot/frc.json') as f:
        config = json.load(f)
    camera = config['cameras'][0]
    width = camera['width']
    height = camera['height']

    # Initialize CameraServer and streams
    CameraServer.enableLogging()
    CameraServer.startAutomaticCapture()
    input_stream = CameraServer.getVideo()
    output_stream = CameraServer.putVideo('Camera 0', width, height)

    # Initialize table for vision output
    inst = NetworkTableInstance.getDefault()
    vision_nt = inst.getTable("vision")
    x_sub = vision_nt.getIntegerTopic("target_x").publish()
    y_sub = vision_nt.getIntegerTopic("target_y").publish()
    inst.startClient4("wpilibpi")
    inst.setServerTeam(4470)
    inst.startDSClient()

    # Allocating new images is very expensive, always try to preallocate
    img = np.zeros(shape=(height, width, 3), dtype=np.uint8)

    # Wait for NetworkTable to start
    time.sleep(0.5)

    while True:
        # Grab frame from camera and make a copy of it
        frame_time, input_img = input_stream.grabFrame(img)
        output_img = np.copy(input_img)

        # Convert to HSV and threshold image for yellow cone
        hsv_img = cv2.cvtColor(input_img, cv2.COLOR_BGR2HSV)
        yellow_mask = cv2.inRange(hsv_img, (18, 71, 167), (39, 255, 255))

        contour_list, hierarchy = cv2.findContours(yellow_mask, mode=cv2.RETR_LIST, method=cv2.CHAIN_APPROX_SIMPLE)
        if len(contour_list) != 0:
            contour = max(contour_list, key=cv2.contourArea)
            if cv2.contourArea(contour) > 200:
                # Draw contour on output image
                cv2.drawContours(output_img, contour, -1, color=(255, 255, 255), thickness=-1)

                m = cv2.moments(contour)
                if m["m00"] != 0:
                    x = int(m["m10"] / m["m00"])
                    y = int(m["m01"] / m["m00"])

                    # Print center coordinates in console and send put on NetworkTable
                    print("Center X: ", x, "| Center Y: ", y)
                    x_sub.set(x)
                    y_sub.set(y)

                    # Draw center circle
                    cv2.circle(output_img, center=(x, y), radius=3, color=(0, 0, 255), thickness=-1)

                    # Draw rectangle on output image
                    # rect = cv2.minAreaRect(contour)
                    # cv2.drawContours(output_img, [cv2.boxPoints(rect).astype(int)], -1, color=(0, 0, 255), thickness=2)

        # Time passed since start of frame and calculate FPS
        # processing_time = time.time() - start_time
        # fps = 1 / processing_time

        # Put FPS on dashboard image
        # cv2.putText(output_img, str(round(fps, 1)), (0, 40), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255))

        # Put output image on dashboard
        output_stream.putFrame(output_img)
        print("active")
main()
