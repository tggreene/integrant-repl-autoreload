(ns ^{:clojure.tools.namespace.repl/load false} integrant-repl-autoreload.core
  (:require [integrant.repl :as igr]
            [nextjournal.beholder :as beholder]))

(defonce watcher nil)

(defn- clojure-file? [filename]
  (re-matches #"[^.].*(\.clj\w?|\.edn)$" filename))

(defn- auto-reset-handler [relevant-file? event]
  (when (relevant-file? (-> event :path .getFileName .toString))
    (binding [*ns* *ns*]
      (integrant.repl/reset))))

(defn- parse-options [path-or-options]
  (merge
   {:relevant-file? clojure-file?
    :paths ["src" "resources"]}
   (if (vector? path-or-options)
     {:paths path-or-options}
     path-or-options)))

(defn start-auto-reset
  "Automatically reset the system when relevant files have changed.

   Accepts the following options:

   * `:paths`, a list of directories that contain the source files to watch
   for changes. Defaults to `[\"src\" \"resources\"]`.
   * `:relevant-file?`, a function that takes a file name and returns a truthy
   value indicating whether the system should be reset. Defaults to a function
   that triggers resets for Clojure and EDN files.

   Accepts a vector of directories as options for backward compatibility."
  ([]
   (start-auto-reset {}))
  ([options]
   (let [{:keys [paths relevant-file?]} (parse-options options)
         callback (partial auto-reset-handler relevant-file?)]
     (alter-var-root #'watcher
                     (fn [watcher]
                       (when-not (nil? watcher)
                         (beholder/stop watcher))
                       (apply beholder/watch callback paths))))))

(defn stop-auto-reset
  []
  (alter-var-root #'watcher
                  (fn [watcher]
                    (when-not (nil? watcher)
                      (beholder/stop watcher)))))
