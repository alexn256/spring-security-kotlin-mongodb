package org.alex256.application

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val app = SpringApplication(Application::class.java)
    app.setBanner { _, _, out ->
        out?.print(
            """
                    
                        __ __      __  ___                   _____            _                _____                      _ __       
                       / //_/___  / /_/ (_)___       __     / ___/____  _____(_)___  ____ _   / ___/___  _______  _______(_) /___  __
                      / ,< / __ \/ __/ / / __ \   __/ /_    \__ \/ __ \/ ___/ / __ \/ __ `/   \__ \/ _ \/ ___/ / / / ___/ / __/ / / /
                     / /| / /_/ / /_/ / / / / /  /_  __/   ___/ / /_/ / /  / / / / / /_/ /   ___/ /  __/ /__/ /_/ / /  / / /_/ /_/ / 
                    /_/ |_\____/\__/_/_/_/ /_/    /_/     /____/ .___/_/  /_/_/ /_/\__, /   /____/\___/\___/\__,_/_/  /_/\__/\__, /  
                                                              /_/                 /____/                                    /____/   


                """.trimIndent()
        )
    }
    app.run(*args)
}