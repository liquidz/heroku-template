(defproject heroku-template "1.0.0-SNAPSHOT"
  :description "heroku template"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "0.6.5"]
                 [ring/ring-jetty-adapter "0.3.8"]
                 [hiccup "0.3.6"]
                 [congomongo "0.1.7"]
                 [clj-spymemcached "1.0.1"]
                 [ring/ring-jetty-adapter "0.3.11"]]
  :dev-dependencies [[ring/ring-devel "0.3.11"]]
  :main heroku-template.server)
