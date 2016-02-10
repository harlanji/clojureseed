(ns example.dev
  (:require [harlanji.clojureseed.web.core :as core]
            [com.stuartsierra.component :as component]))

(defonce _ (.warn js/console "Dev mode loaded -- hack responsibly."))

(defrecord DevMode []
           component/Lifecycle
           (start [component]
                  (.info js/console "Dev mode on. :)")
                  component)
           (stop [component]
                 (.info js/console "Dev mode off.")
                 component))

(swap! core/system update :dev #(or % (component/using (map->DevMode {})
                                                       {:app :app})))

(defn reload! [& _]
      (.info js/console (str "Dev Reload. system=" @core/system))
      (swap! core/system #(-> %
                              component/stop
                              component/start)))