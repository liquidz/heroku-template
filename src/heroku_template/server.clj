(ns heroku-template.server
  (:use
    heroku-template.core
    [ring.adapter.jetty :only [run-jetty]]
    [ring.middleware params]))

(def app
  (-> main-routes
    wrap-params))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))
