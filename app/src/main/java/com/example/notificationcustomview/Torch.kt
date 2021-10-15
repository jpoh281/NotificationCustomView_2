package com.example.notificationcustomview

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

class Torch(context: Context) {   // CameraManager 객체를 얻어야 하므로 Context를 생성자로 받았음

    companion object {
        var state : Boolean = false
    }
    private var cameraId: String? = null
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE)
            as CameraManager   // getSystemService()의 리턴값이 object형이므로 as로 형변환했음

    init {                         // 클래스가 초기화 될 때 실행됨
        cameraId = getCameraId()
    }

    fun flashOn() {
        if (cameraId != null){ cameraManager.setTorchMode(cameraId!!, true)
            state = true
        }
    }

    fun flashOff() {
        if (cameraId != null) {cameraManager.setTorchMode(cameraId!!, false)
            state = false
        }
    }

    private fun getCameraId():String? { // 카메라 ID는 각각의 내장 카메라에 부여된 고유의 ID이다
        // 카메라가 없다면 null을 반환해야 하므로 리턴형을 String?로 지정
        val cameraIds = cameraManager.cameraIdList   // 기기가 가진 모든 카메라 목록
        for (id in cameraIds) {
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) // // 플래시 가능 여부
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING)   // 카메라 랜즈의 방향
            if (flashAvailable != null && flashAvailable && (lensFacing != null) && (lensFacing > 0)
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                // 플래시가 가능하고 카메라 방향이 뒷방향
                return id
            }
        }
        return null
    }
}