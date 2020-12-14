package cn.cheney.flutter.pink.router.channel

class PageObserverReceiverChannel(private val channel: ChannelProxy) {

    init {
        create()
        onWillAppear()
        onDidAppear()
        onWillDisappear()
        onDidDisappear()
        onDestroy()
    }

    private fun create() {
        channel.registerMethod("create") { args, result ->

            result.success(true)
        }
    }

    private fun onWillAppear() {
        channel.registerMethod("willAppear") { args, result ->

            result.success(true)
        }
    }

    private fun onDidAppear() {
        channel.registerMethod("didAppear") { args, result ->

            result.success(true)
        }
    }

    private fun onWillDisappear() {
        channel.registerMethod("willDisappear") { args, result ->

            result.success(true)
        }
    }

    private fun onDidDisappear() {
        channel.registerMethod("didDisappear") { args, result ->

            result.success(true)
        }
    }

    private fun onDestroy() {
        channel.registerMethod("destroy") { args, result ->

            result.success(true)
        }
    }


}