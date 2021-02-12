# integrant-repl-autoreload

Simple integrant autoreloading using hawk, borrowed from duct

## Installation

[![Clojars Project](https://img.shields.io/clojars/v/tggreene/integrant-repl-autoreload.svg)](https://clojars.org/tggreene/integrant-repl-autoreload)

    [tggreene/integrant-repl-autoreload "0.1.0"]

## Usage

Require in your dev namespace:

    (ns dev
      (:require [integrant.repl :as igr]
                [integrant-repl-autoreload.core :as igr-auto]))

Run alongside integrant-repl:

    (igr/set-prep! (some system prep))
    (igr/go)
    (igr-auto/start-auto-reset)

Switch back to manual reset:

    (igr-auto/stop-auto-reset)

## License

Copyright © 2021 Tim Greene

Distributed under the Eclipse Public License version 1.0.
