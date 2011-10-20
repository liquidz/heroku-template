(ns heroku-template.core
  (:use
    [heroku-template.addons mongo memcache]
    [compojure.core :only [defroutes GET POST]]
    [hiccup.core :only [html]]
    somnium.congomongo
    clj-spymemcached.core)
  (:require
    [compojure.route :as route]))

(defn layout [& {:keys [title head body]}]
  (html
    [:html
     [:head
      [:title title] head ]
    [:body body]]))

(defn index []
  (let [counter
        (fetch-and-modify
          :firstcollection
          {:_id "counter"}
          {:$inc {:value 1} }
          :return-new true :upsert? true)]
    (layout
      :title "hello"
      :body [:p (str "Welcome heroku-template, you're visitor " (or (:value counter) 0))])))

(defroutes main-routes
  (GET "/" _
    (init-mongodb)
    (index))

  (GET "/get" _
    (init-memcache)
    (cache-get :test :default "no data"))

  (GET "/set/:value" [value]
    (init-memcache)
    (cache-set :test value :expiration 60)
    "ok")

  (route/files "/static/")
  (route/not-found "<h1>page not found</h1>"))


