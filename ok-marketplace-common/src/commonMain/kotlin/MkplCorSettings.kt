package ru.otus.otuskotlin.marketplace.common

import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository


data class MkplCorSettings(
    val repoStub: IAdRepository = IAdRepository.NONE,
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
) {
    companion object {
        val NONE = MkplCorSettings()
    }
}
