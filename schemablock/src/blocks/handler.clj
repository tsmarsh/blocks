(ns blocks.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [outpace.config :refer [defconfig]]
            [clojure.spec.alpha :as s]
            [blocks.validator :refer :all]))

(defconfig prefix "bob")

(defn validate [json]
  (if (s/valid? :todo/todo json)
    (response json)
    {:status 400 :headers {} :body (s/explain-data :todo/todo json)}))

(defroutes app-routes
           (GET "/" [] "OK")
           (POST "/" {body :body} (validate body))
           (route/not-found "Not Found"))

(def app (-> app-routes
             (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
             (wrap-json-body {:keywords? true})
             (wrap-json-response {:pretty true})))