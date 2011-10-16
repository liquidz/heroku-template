(ns heroku-template.server
  (:use
    heroku-template.core
    [ring.adapter.jetty :only [run-jetty]]
    [ring.middleware params stacktrace reload]))

(def develop?
  (= "true" (get (System/getenv) "HEROKU_DEVELOP" "false")))

(def app
  (let [_reload (if develop?  #(wrap-reload % '[heroku-template.core]) identity)
        _stacktrace (if develop? wrap-stacktrace identity)]
    (-> main-routes
      wrap-params
      _reload
      _stacktrace)))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))
