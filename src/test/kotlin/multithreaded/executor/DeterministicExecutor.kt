package multithreaded.executor

import java.util.concurrent.Executor


/**
 * An [Executor] that executes commands on the thread that calls
 * [runPendingCommands][.runPendingCommands] or [runUntilIdle][.runUntilIdle].
 * This is useful when using Mock Objects to test code that spawns background tasks.
 *
 * @author nat
 */
class DeterministicExecutor : Executor {
    private var commands: MutableList<Runnable> = ArrayList()

    /**
     * Returns whether this executor is idle -- has no pending background tasks waiting to be run.
     *
     * @return true if there are no background tasks to be run, false otherwise.
     * @see .runPendingCommands
     * @see .runUntilIdle
     */
    val isIdle: Boolean
        get() = commands.isEmpty()

    /**
     * Runs all commands that are currently pending. If those commands also
     * schedule commands for execution, the scheduled commands will *not*
     * be executed until the next call to [.runPendingCommands] or
     * [.runUntilIdle].
     */
    fun runPendingCommands() {
        val commandsToRun: List<Runnable> = commands
        commands = ArrayList()
        for (command in commandsToRun) {
            command.run()
        }
    }

    /**
     * Runs executed commands until there are no commands pending execution, but
     * does not tick time forward.
     */
    fun runUntilIdle() {
        while (!isIdle) {
            runPendingCommands()
        }
    }

    override fun execute(command: Runnable) {
        commands.add(command)
    }
}
