package com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase

data class FileUseCases(
    val getNestedFileItems: GetNestedFileItems,
    val orderFiles: OrderFiles,
    val compareFiles: CompareFiles,
    val getFileHashInfoList: GetFileHashInfoList,
    val insertNestedFilesHashInfoList: InsertNestedFilesHashInfoList
)