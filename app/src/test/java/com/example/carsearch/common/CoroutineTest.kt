package com.example.carsearch.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * JUnit Test that sets the main dispatcher to a [StandardTestDispatcher]
 * for unit testing coroutines that use Dispatchers.Main.
 */
class CoroutineTest : TestWatcher() {
    private val testDispatcher = StandardTestDispatcher()

    /**
     * Initializes the rule by setting the main dispatcher to [testDispatcher].
     *
     * @param description Contains details of the test to be started; not used directly here.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * Cleans up after the test by resetting the main dispatcher to its original state.
     *
     * @param description Contains details of the test that finished; not used directly here.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}