LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS) 
LOCAL_MODULE    := achro4-device
LOCAL_SRC_FILES := fpga-push-switch-jni.c


LOCAL_LDLIBS    := -L$(SYSROOT)/usr/lib -llog 

include $(BUILD_SHARED_LIBRARY)

