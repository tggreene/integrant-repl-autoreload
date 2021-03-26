(ns integrant-repl-autoreload.core
  (:require [clojure.tools.namespace.repl :as repl]
            [hawk.core :as hawk]
            [integrant.repl :as igr]))

(repl/disable-reload! (find-ns 'integrant-repl-autoreload.core))

(defonce hawk-watcher nil)

(defn- auto-reset-handler [ctx _event]
  (binding [*ns* *ns*]
    (integrant.repl/reset)
    ctx))

(defn- clojure-file? [_ {:keys [file]}]
  (re-matches #"[^.].*(\.clj|\.edn)$" (.getName file)))

(defn start-auto-reset
  "Automatically reset the system when a Clojure or edn file is changed in
  `src` or `resources`."
  ([]
   (start-auto-reset ["src" "resources"]))
  ([paths]
   (alter-var-root #'hawk-watcher
                   (fn [watcher]
                     (when-not (nil? watcher)
                       (hawk/stop! watcher))
                     (hawk/watch! [{:paths paths
                                    :filter clojure-file?
                                    :handler auto-reset-handler}])))))

(defn stop-auto-reset
  []
  (alter-var-root #'hawk-watcher
                  (fn [watcher]
                    (when-not (nil? watcher)
                      (hawk/stop! watcher)))))
