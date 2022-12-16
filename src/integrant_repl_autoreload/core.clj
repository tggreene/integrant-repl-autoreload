(ns ^{:clojure.tools.namespace.repl/load false} integrant-repl-autoreload.core
  (:require [integrant.repl :as igr]
            [nextjournal.beholder :as beholder]))

(defonce watcher nil)

(defn- clojure-file? [path]
  (re-matches #"[^.].*(\.clj\w?|\.edn)$" (-> path .getFileName .toString)))

(defn- auto-reset-handler [event]
  (when (clojure-file? (:path event))
    (binding [*ns* *ns*]
      (integrant.repl/reset))))

(defn start-auto-reset
  "Automatically reset the system when a Clojure or edn file is changed, pass a
  vector of relative paths or `[\"src\" \"resources\"]` by default"
  ([]
   (start-auto-reset ["src" "resources"]))
  ([paths]
   (alter-var-root #'watcher
                   (fn [watcher]
                     (when-not (nil? watcher)
                       (beholder/stop watcher))
                     (apply beholder/watch auto-reset-handler paths)))))

(defn stop-auto-reset
  []
  (alter-var-root #'watcher
                  (fn [watcher]
                    (when-not (nil? watcher)
                      (beholder/stop watcher)))))
