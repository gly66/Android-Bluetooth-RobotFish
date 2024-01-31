# Robotic Fish Swimming Trajectory Visualization System Based on Android Mobile Phone

An Android cell phone based system to visualize the
swimming trajectory of robot fish. The system includes two platforms: robot fish and
Android cell phone. It can be divided into robot fish module, positioning module, data
transmission module and trajectory visualization module by functional modules. Positioning module adopts inertial positioning algorithm, through the inertial sensing
unit (IMU) integrated with accelerometer and gyroscope to get the acceleration and
angular velocity of the object in three directions, and then use the calculus principle to
integrate the motion state of the object to get the real-time position of the robot fish. The communication module adopts Bluetooth wireless communication, which is
responsible for uploading IMU data to the cell phone in real time. The trajectory
visualization module mainly does data processing and trajectory visualization. These
modules work together to complete the tracking and positioning of the robot fish as
well as the trajectory visualization.

**Based Framework:**  
https://github.com/Jasonchenlijian/FastBle