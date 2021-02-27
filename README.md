# KBotExamples

Examples for [KBot](https://github.com/Kraktun/KBot)

To use it:

* Change token and username for the bot and replace the creator in [BotConfig.kt](src/main/kotlin/com/kraktun/kbotexample/BotConfig.kt)
* Define some custom commands (some examples are available in the [commands](src/main/kotlin/com/kraktun/kbotexample/commands) folder)
* Define a bot similar to [TestBot.kt](src/main/kotlin/com/kraktun/kbotexample/bots/TestBot.kt) or [ServerBot.kt](src/main/kotlin/com/kraktun/kbotexample/bots/ServerBot.kt) that implements those commands
* Create a data source to manage users (an example with a sqlite db is provided in [data](src/main/kotlin/com/kraktun/kbotexample/data))
* Initialize the bot in [Main.kt](src/main/kotlin/com/kraktun/kbotexample/Main.kt)

More advanced configurations with ask-answer commands and callbacks will be available later on.
