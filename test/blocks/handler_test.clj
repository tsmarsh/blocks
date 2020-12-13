(ns blocks.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [blocks.handler :refer :all]
            [cheshire.core :as c]))

(deftest test-app
  (testing "heartbeat"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "OK"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest validation
  (testing "validation"
    (let [body (slurp "./test-resources/good.json")
          request (mock/request :post "/" body)
          req2 (assoc-in request [:headers "content-type"]  "application/json")
          response (app req2)]
      (is (= (:status response) 200))
      (is (= (c/parse-string (:body response)) (c/parse-string body))))))

(deftest failure
  (testing "failure"
    (let [body (slurp "./test-resources/bad.json")
          request (mock/request :post "/" body)
          req2 (assoc-in request [:headers "content-type"]  "application/json")
          response (app req2)]
      (is (= (:status response) 400)))))
