(ns blockserver.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [outpace.config :refer [defconfig]]
            [clojure.spec.alpha :as s]))

(defconfig prefix "bob")

(defn validate
  [json]
  (if (s/valid? :todo/todo json)
    (response json)
    {:status 400 :headers {} :body (s/explain-data :todo/todo json)}))

(defn build-routes
  [body-handler]
  (defroutes app-routes
             (GET "/" [] "OK")
             (POST "/" {body :body} (body-handler body))
             (route/not-found "Not Found")))

(defn app
  [body-handler]
  (-> (build-routes body-handler)
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-json-body {:keywords? true})
      (wrap-json-response {:pretty true})))