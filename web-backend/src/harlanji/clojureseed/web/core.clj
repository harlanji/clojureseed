(ns harlanji.clojureseed.web.core
    (:require [ring.middleware.resource :refer [wrap-resource]]
              [org.httpkit.server :refer [run-server]]
              [taoensso.sente :as sente]
              [taoensso.sente.server-adapters.http-kit :refer [sente-web-server-adapter]]
              [ring.middleware.keyword-params :refer [wrap-keyword-params]]
              [ring.middleware.params :refer [wrap-params]]
              [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
              [ring.middleware.session :refer [wrap-session]]
              [clojure.core.async :refer [go <!]]))

(defn say-hi [req]
  (when (.startsWith (:uri req) "/hi")
    (println "/hi")
    {:status 200 :body "haiii" :headers {"Content-Type" "text/plain"}}))

(defn sente-handler [uri]
  (let [{:keys [ch-recv
                send-fn
                ajax-post-fn
                ajax-get-or-ws-handshake-fn
                connected-uids]}
        (sente/make-channel-socket! sente-web-server-adapter {})]
    (go (while true
          (let [event (<! ch-recv)]
            (println "event:" (:id event)))))
    (fn [req]
      (when (= (:uri req) uri)
        (println "Starting sente session.")
        (case (:request-method req)
          :post (ajax-post-fn req)
          :get (ajax-get-or-ws-handshake-fn req)
          :else nil)))))

(defn wrap-cors-all [handler]
  (fn [req]
    (update (handler req) :headers #(assoc % "Access-Control-Allow-Origin" "*"))))

(def app (let [chsk (sente-handler "/chsk")
              ]
           (-> #(or (say-hi %)
                    (chsk %))
               (wrap-resource "public")
               wrap-cors-all
               wrap-keyword-params
               wrap-params
               wrap-anti-forgery
               wrap-session
               )))

(defn start! [port]
      (println "server on. port: " port)
      (run-server app {:port port}))