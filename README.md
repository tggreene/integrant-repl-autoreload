# integrant-repl-autoreload

Simple integrant autoreloading using hawk, borrowed from duct.

You can use this library to get mount-like hot reloading behaviour for integrant
that will watch for file system changes and fire an `integrant.repl/reset`
whenever a clojure file change is detected.

It can be used as an alternative to ring's wrap-reload in an integrant system
for automatically reloading a server handler without having to manually restart
the server (assuming that you've captured the server and/or handler as integrant
components).

## Installation

[![Clojars Project](https://img.shields.io/clojars/v/tggreene/integrant-repl-autoreload.svg)](https://clojars.org/tggreene/integrant-repl-autoreload)

    [com.github.tggreene/integrant-repl-autoreload "0.2.0"]

Or deps.edn:

    com.github.tggreene/integrant-repl-autoreload {:mvn/version "0.2.0"} 

## Usage

Require in your dev (or user) namespace:

    (ns dev
      (:require [integrant.repl :as igr]
                [integrant-repl-autoreload.core :as igr-auto]))

Run alongside integrant-repl:

    (igr/set-prep! (some system prep))
    (igr/go)
    (igr-auto/start-auto-reset)
    ;; or
    (igr-auto/start-auto-reset ["src" "resources"])

Switch back to manual reset:

    (igr-auto/stop-auto-reset)

## License

Copyright © 2021 Tim Greene

Distributed under the Eclipse Public License version 1.0.
