(ns heroku-template.dev
  (:use
    [ring.adapter.jetty :only [run-jetty]]
    [ring.middleware reload stacktrace])
  (:require [heroku-template.server :as serv])
  )


(def dev-app
  (-> serv/app
    (wrap-reload '[heroku-template.core])
    wrap-stacktrace))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty dev-app {:port port})))
