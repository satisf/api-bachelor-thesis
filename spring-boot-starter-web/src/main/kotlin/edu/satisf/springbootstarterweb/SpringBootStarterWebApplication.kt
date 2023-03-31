package edu.satisf.springbootstarterweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootStarterWebApplication

fun main(args: Array<String>) {
    runApplication<SpringBootStarterWebApplication>(*args)
}
