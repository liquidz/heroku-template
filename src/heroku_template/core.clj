(ns heroku-template.core
  (:use
    [heroku-template.addons mongo memcache]
    [compojure.core :only [defroutes GET POST]]
    [hiccup.core :only [html]]
    net.cgrand.enlive-html
    somnium.congomongo
    clj-spymemcached.core)
  (:require
    [compojure.route :as route]))


(deftemplate index-html "html/index.html" [title value]
  [:title] (content title)
  [:h1] (content title)
  [:#value] (content (str value)))

(defn index []
  (let [counter (fetch-and-modify
                  :firstcollection
                  {:_id "counter"}
                  {:$inc {:value 1} }
                  :return-new true :upsert? true)]
    (apply str (index-html "heroku-template" (or (:value counter) 0)))))

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


