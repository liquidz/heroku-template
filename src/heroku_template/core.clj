(ns heroku-template.core
  (:use
    heroku-template.mongo
    [compojure.core :only [defroutes GET POST]]
    [hiccup.core :only [html]]
    somnium.congomongo)
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
  (route/not-found "<h1>page not found</h1>"))


