import org.gradle.api.Project

fun Project.printTaskGraph() {
    gradle.taskGraph.whenReady {
        println( "Tasks")
        allTasks.forEachIndexed { n, task ->
            println ("${n + 1} $task")
            task.dependsOn.forEachIndexed { m, depObj ->
                println ("  ${ m + 1 } $depObj")
            }
        }
    }
}