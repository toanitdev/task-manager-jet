package com.toanitdev.taskmanager.domain.interactors

abstract class UseCase<Param, Result> {

    abstract fun execute(param: Param) : Result

}