(ns harlanji.clojureseed.web.main
    (:require [harlanji.clojureseed.web.core :as core]
              [environ.core :refer [env]]
              ;[clojure.tools.cli :refer [parse-opts]]
      )
    (:gen-class))

; this file exists as a shim between boot loading and reloadable core code. if the need to AOT goes away, this can too.

(def cli-options [["-p" "--port Port" "Port Number"
                   :default 3000
                   :parse-fn #(Integer/parseInt %)]])


(defn -main []
  (let [port (Integer/parseInt (env :port))]
    (core/start! port)))