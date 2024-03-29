(defproject heroku-template "1.0.0-SNAPSHOT"
  :description "heroku template"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/data.json "0.1.1"]
                 [compojure "0.6.5"]
                 [ring/ring-jetty-adapter "0.3.8"]
                 [enlive "1.0.0"]
                 [congomongo "0.1.7"]
                 [clj-spymemcached "1.0.1"]
                 [ring/ring-jetty-adapter "0.3.11"]]
  :dev-dependencies [[ring/ring-devel "0.3.11"]]
  :web-content "public"
  :main heroku-template.server)
