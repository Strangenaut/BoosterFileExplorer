package com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase

import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileHashInfo
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileItem

class CompareFiles {

    operator fun invoke(files: List<FileItem>, hashInfoList: List<FileHashInfo>) {
        for (file in files) {
            val hashInfo = hashInfoList.find {
                file.path == it.path
            }
            if (hashInfo != null) {
                file.hasChangedSinceLastLaunch =
                    file.fileHashCode != hashInfo.fileHashCode
            }
        }
    }
}