(ns currency-map-client.app
  (:require [goog.net.XhrIo :as xhr]
            [goog.dom :as dom]
            [cljs.core.async :as a :refer [put! chan <! >! close!]]
            [cljs.reader :as reader]
            [clojure.string])
  (:require-macros [cljs.core.async.macros :as async-macros :refer [go]]))
(enable-console-print!)

(extend-type js/NodeList
  ISeqable
  (-seq [array] (array-seq array 0)))

(def currency-country-map
  (let [a (atom [])]
    (go (xhr/send "currency-country-mapping.edn"
                  (fn [evt] (let [res (-> evt .-target .getResponseText reader/read-string)]
                              (reset! currency-country-map res)))))
    a))

(defn init []
  (let [c (.. js/document (createElement "DIV"))]
    (aset c "innerHTML" "<p>i'm dynamically created</p>")
    (.. js/document (getElementById "container") (appendChild c))))

;;(go (println (<! (get-fx-rate-for-date "2016-05-22"))))
;;(years-ago 0)
#_(http-get "http://apilayer.net/api/historical?access_key=2b54e936f1a7e0944f03cd840189b813&date=2014-05-22" #(js/console.log ))

(defn get-dom-node-for-country-code [country-code]
  (first (goog.dom.getElementsByClass country-code)))

(defn color-a-country! [country-code color]
  (set! (-> (get-dom-node-for-country-code (clojure.string/lower-case country-code)) .-style .-fill) color))

(def currency-to-country-mapping [["NZ" "NZD"] ["CK" "NZD"] ["NU" "NZD"] ["PN" "NZD"] ["TK" "NZD"] ["AU" "AUD"] ["CX" "AUD"] ["CC" "AUD"] ["HM" "AUD"] ["KI" "AUD"] ["NR" "AUD"] ["NF" "AUD"] ["TV" "AUD"] ["AS" "EUR"] ["AD" "EUR"] ["AT" "EUR"] ["BE" "EUR"] ["FI" "EUR"] ["FR" "EUR"] ["GF" "EUR"] ["TF" "EUR"] ["DE" "EUR"] ["GR" "EUR"] ["GP" "EUR"] ["IE" "EUR"] ["IT" "EUR"] ["LU" "EUR"] ["MQ" "EUR"] ["YT" "EUR"] ["MC" "EUR"] ["NL" "EUR"] ["PT" "EUR"] ["RE" "EUR"] ["WS" "EUR"] ["SM" "EUR"] ["SI" "EUR"] ["ES" "EUR"] ["VA" "EUR"] ["GS" "GBP"] ["GB" "GBP"] ["JE" "GBP"] ["IO" "USD"] ["GU" "USD"] ["MH" "USD"] ["FM" "USD"] ["MP" "USD"] ["PW" "USD"] ["PR" "USD"] ["TC" "USD"] ["US" "USD"] ["UM" "USD"] ["VG" "USD"] ["VI" "USD"] ["HK" "HKD"] ["CA" "CAD"] ["JP" "JPY"] ["AF" "AFN"] ["AL" "ALL"] ["DZ" "DZD"] ["AI" "XCD"] ["AG" "XCD"] ["DM" "XCD"] ["GD" "XCD"] ["MS" "XCD"] ["KN" "XCD"] ["LC" "XCD"] ["VC" "XCD"] ["AR" "ARS"] ["AM" "AMD"] ["AW" "ANG"] ["AN" "ANG"] ["AZ" "AZN"] ["BS" "BSD"] ["BH" "BHD"] ["BD" "BDT"] ["BB" "BBD"] ["BY" "BYR"] ["BZ" "BZD"] ["BJ" "XOF"] ["BF" "XOF"] ["GW" "XOF"] ["CI" "XOF"] ["ML" "XOF"] ["NE" "XOF"] ["SN" "XOF"] ["TG" "XOF"] ["BM" "BMD"] ["BT" "INR"] ["IN" "INR"] ["BO" "BOB"] ["BW" "BWP"] ["BV" "NOK"] ["NO" "NOK"] ["SJ" "NOK"] ["BR" "BRL"] ["BN" "BND"] ["BG" "BGN"] ["BI" "BIF"] ["KH" "KHR"] ["CM" "XAF"] ["CF" "XAF"] ["TD" "XAF"] ["CG" "XAF"] ["GQ" "XAF"] ["GA" "XAF"] ["CV" "CVE"] ["KY" "KYD"] ["CL" "CLP"] ["CN" "CNY"] ["CO" "COP"] ["KM" "KMF"] ["CD" "CDF"] ["CR" "CRC"] ["HR" "HRK"] ["CU" "CUP"] ["CY" "CYP"] ["CZ" "CZK"] ["DK" "DKK"] ["FO" "DKK"] ["GL" "DKK"] ["DJ" "DJF"] ["DO" "DOP"] ["TP" "IDR"] ["ID" "IDR"] ["EC" "ECS"] ["EG" "EGP"] ["SV" "SVC"] ["ER" "ETB"] ["ET" "ETB"] ["EE" "EEK"] ["FK" "FKP"] ["FJ" "FJD"] ["PF" "XPF"] ["NC" "XPF"] ["WF" "XPF"] ["GM" "GMD"] ["GE" "GEL"] ["GI" "GIP"] ["GT" "GTQ"] ["GN" "GNF"] ["GY" "GYD"] ["HT" "HTG"] ["HN" "HNL"] ["HU" "HUF"] ["IS" "ISK"] ["IR" "IRR"] ["IQ" "IQD"] ["IL" "ILS"] ["JM" "JMD"] ["JO" "JOD"] ["KZ" "KZT"] ["KE" "KES"] ["KP" "KPW"] ["KR" "KRW"] ["KW" "KWD"] ["KG" "KGS"] ["LA" "LAK"] ["LV" "LVL"] ["LB" "LBP"] ["LS" "LSL"] ["LR" "LRD"] ["LY" "LYD"] ["LI" "CHF"] ["CH" "CHF"] ["LT" "LTL"] ["MO" "MOP"] ["MK" "MKD"] ["MG" "MGA"] ["MW" "MWK"] ["MY" "MYR"] ["MV" "MVR"] ["MT" "MTL"] ["MR" "MRO"] ["MU" "MUR"] ["MX" "MXN"] ["MD" "MDL"] ["MN" "MNT"] ["MA" "MAD"] ["EH" "MAD"] ["MZ" "MZN"] ["MM" "MMK"] ["NA" "NAD"] ["NP" "NPR"] ["NI" "NIO"] ["NG" "NGN"] ["OM" "OMR"] ["PK" "PKR"] ["PA" "PAB"] ["PG" "PGK"] ["PY" "PYG"] ["PE" "PEN"] ["PH" "PHP"] ["PL" "PLN"] ["QA" "QAR"] ["RO" "RON"] ["RU" "RUB"] ["RW" "RWF"] ["ST" "STD"] ["SA" "SAR"] ["SC" "SCR"] ["SL" "SLL"] ["SG" "SGD"] ["SK" "SKK"] ["SB" "SBD"] ["SO" "SOS"] ["ZA" "ZAR"] ["LK" "LKR"] ["SD" "SDG"] ["SR" "SRD"] ["SZ" "SZL"] ["SE" "SEK"] ["SY" "SYP"] ["TW" "TWD"] ["TJ" "TJS"] ["TZ" "TZS"] ["TH" "THB"] ["TO" "TOP"] ["TT" "TTD"] ["TN" "TND"] ["TR" "TRY"] ["TM" "TMT"] ["UG" "UGX"] ["UA" "UAH"] ["AE" "AED"] ["UY" "UYU"] ["UZ" "UZS"] ["VU" "VUV"] ["VE" "VEF"] ["VN" "VND"] ["YE" "YER"] ["ZM" "ZMK"] ["ZW" "ZWD"] ["AX" "EUR"] ["AO" "AOA"] ["AQ" "AQD"] ["BA" "BAM"] ["CD" "CDF"] ["GH" "GHS"] ["GG" "GGP"] ["IM" "GBP"] ["LA" "LAK"] ["MO" "MOP"] ["ME" "EUR"] ["PS" "JOD"] ["BL" "EUR"] ["SH" "GBP"] ["MF" "ANG"] ["PM" "EUR"] ["RS" "RSD"] ["USAF" "USD"]])
