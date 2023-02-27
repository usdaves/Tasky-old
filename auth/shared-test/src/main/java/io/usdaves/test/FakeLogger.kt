package io.usdaves.test

import io.mockk.mockk
import io.usdaves.logger.Logger

// Created by usdaves(Usmon Abdurakhmanov) on 2/24/2023

class FakeLogger {

  val mock: Logger = mockk(relaxUnitFun = true)
}
