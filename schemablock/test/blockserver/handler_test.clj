(ns blockserver.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [blockserver.handler :refer :all]
            [cheshire.core :as c]))

(deftest test-app
  (testing "heartbeat"
    (let [response ((app (fn [] nil)) (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "OK"))))

  (testing "not-found route"
    (let [response ((app (fn [] nil)) (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest validation
  (testing "validation"
    (let [body {:body 4}
          request (mock/request :post "/" (c/encode body))
          req2 (assoc-in request [:headers "content-type"]  "application/json")
          response ((app #(do (is (= {:body 4} %))
                              {:status 200 :body %})) req2)]
      (is (= (:status response) 200))
      (is (= body (c/parse-string (:body response) keyword))))))

(deftest failure
  (testing "failure"
    (let [body {:body 4}
          request (mock/request :post "/" (c/encode body))
          req2 (assoc-in request [:headers "content-type"]  "application/json")
          response ((app (fn [b] {:status 400 :headers {} :body b})) req2)]
      (is (= (:status response) 400)))))
