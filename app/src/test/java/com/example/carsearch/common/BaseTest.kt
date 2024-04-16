package com.example.carsearch.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Base class for all unit tests. Sets up rules for managing architecture components
 * and coroutine behavior in tests.
 */
abstract class BaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    /**
     * Initializes MockK annotations before each test and calls init for further setup.
     */
    @Before
    open fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        init()
    }

    /**
     * Clean up after tests, especially important for resetting coroutine contexts.
     */
    @After
    open fun tearDown() {
        coroutineRule.tearDown()
    }

    /**
     * Placeholder for additional initialization by subclasses.
     */
    open fun init() {
    }

    /**
     * Checks whether a Result is a failure with a specific message.
     */
    fun <T> isFailureWithMessage(result: Result<T>, message: String): Boolean {
        var errorMessage: String? = null
        result.onFailure { errorMessage = it.message }
        return result.isFailure && errorMessage?.contains(message) == true
    }
}

/**
 * Test rule for managing coroutines in testing environment by replacing the Main dispatcher
 * with a test dispatcher.
 */
class CoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestRule {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun apply(base: Statement?, description: Description?): Statement = object : Statement() {
        override fun evaluate() {
            Dispatchers.setMain(testDispatcher)

            base?.evaluate()

            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}