(ns a2
	(:require [clojure.string :as str])
	(:use [clojure.string :only (split)])
	(:use [clojure.string :only [index-of]])
	(:use [clojure.string :only [trim]])
)


(def custDispMap)
(def prodDispMap)
(def salesDispMap)

(defn custPrinter[]
	(println)
	(def printMe  (apply str(map #(str (first %)(second %)
	"\n-------------------------------------------------------\n")custDispMap)))
	(println printMe)
	(println)
)
;;---------------------------------------------------
(defn prodPrinter[]
	(println)
	(def printMe  (apply str(map #(str (first %)(second %)
	"\n-------------------------------------------------------\n")prodDispMap)))
	(println printMe)
	(println)
)
;;---------------------------------------------------
(defn salesPrinter[]
	(println)
	
	;;1)read a sales record 2)for given CID you have read the name 3)now read the prodName using prodID 4)last is quant as it is
	
	(def custID( apply str(map (fn [[key val]] ;;looks like salesID  |custID|productId|quant
		(def cID (subs val 1 2))
		
		(def cName(get custDispMap (Integer/parseInt( apply str (filter 
			(fn [x] (= x  (Integer/parseInt cID)))(keys custDispMap)
		)))))
		(def cName (subs cName 1))
		(def cName (subs cName 0 (index-of cName "|")))
		
		(def pID (subs val 3 4))
		(def pName(get prodDispMap (Integer/parseInt( apply str (filter 
			(fn [x] (= x  (Integer/parseInt pID)))(keys prodDispMap)
		)))))
		(def pName (subs pName 1))
		(def pName (subs pName 0 (index-of pName "|")))
		
		(def quant (str(subs val 5 6)))
		
		(println key"|"cName"|"pName"|"quant)
		(println "-------------------------------------------------------")
	
	)salesDispMap)))
	(println)
)
;;---------------------------------------------------
(defn custTotalSale[]

	(println "Enter customer's name you want to find total value of the purchases for")
	(def cName (read-line))
	(def salesTot 0.0)
	(def cID -1)

	;;APPROACH - 1 get CustID for input,  2 get sales records for that custID, 
	;;3 forallRecords{get prodID for that sales record,  then totSale  = totSale + (quant x price) }

	(def custID( apply str(map (fn [[key val]] 
		(def valStr val)
		(def valStr (subs valStr 1))
		(if(= (subs valStr 0 (index-of valStr "|")) cName)
			(def cID key)
		)
		)custDispMap))
	)

	(if(= cID -1)
		(println "Customer not found")
	
		(def custID( apply str(map (fn [[key val]] 
				(def valStrS val)
				(def salesCustID (nth(split valStrS #"\|")1) )
				(def salesCustID (Integer/parseInt salesCustID))
				(if(= salesCustID cID)
					(do
						(def prodID (Integer/parseInt(nth(split valStrS #"\|")2)))
						
						(def custoID( apply str(map (fn [[key val]] 
							(def valP val)
							(if(= key prodID)
								(do
									(def price (Float/parseFloat (nth(split valP #"\|")2)))
									(def quant (Integer/parseInt (nth(split valStrS #"\|")3)))
									(def bill (*  price quant)) 
									(def salesTot (+ salesTot bill))
								)
							)
							)prodDispMap))
						)
					)
				)
			)salesDispMap))
		)
	)
	(if(> cID -1) 
		(println cName"'s Total:$" (format "%.2f" salesTot))
	)
	(println "")
)
	

;;---------------------------------------------------
(defn prodTotalSaleCount[]

;; APPROACH : match prodID from inputted prodName. fetch all records for that prodID. forall(count = count + quant)

	(println "Enter product's name you want to find total number of sales for")
	(def prodName (read-line))
	(def salesNum 0)
	(def prodID -1)
	
	(def custID( apply str(map (fn [[key val]] 
		(def valStr val)
		(def valStr (subs valStr 1))
		(if(= (subs valStr 0 (index-of valStr "|")) prodName)
			(def prodID key)
		)
		)prodDispMap))
	)
	
	(if(= prodID -1)
		(println "Product not found")
		
		(def custerID( apply str(map (fn [[key val]] 
			(def valStrS val)
			(def prodRecCount (nth(split valStrS #"\|")3) )	
			(def prodRecCount (Integer/parseInt prodRecCount))
			
			(def recProdID (nth(split valStrS #"\|")2))
			(def recProdID (Integer/parseInt recProdID))
			
			(if(= prodID recProdID)
				(do
					(def salesNum (+ salesNum prodRecCount))
				)
			)
			)salesDispMap))
		)
	)
	(if(> prodID -1)
		(println "Total sales for" prodName ":"salesNum))
	(println "")
)

;;---------------------------------------------------
(defn custReader []
	(def custMap(sorted-map))
	
	(def strFile (slurp "cust.txt"))
	(def cstmrList (clojure.string/split-lines strFile))  
	
	(def custerID( apply str(map (fn [record] 
			(def keyID (nth (split record #"\|" 2) 0) )
			(def valueStr (clojure.string/join ["|" (nth (split record #"\|" 2 ) 1)]))
			
			(def custTempMap (sorted-map (Integer/parseInt keyID) valueStr))
			(def custMap (merge custMap custTempMap))
			
			)cstmrList)
		)
	)
	(def custDispMap custMap)
)
;;--------------------------------------------------------------
(defn prodReader []
	(def prodMap(sorted-map))
	
	(def strFile (slurp "prod.txt"))
	(def prodList (clojure.string/split-lines strFile))  
	(def custerID( apply str(map (fn [record] 
			(def keyID (nth (split record #"\|" 2) 0) )
			(def valueStr (clojure.string/join ["|" (nth (split record #"\|" 2 ) 1)]))
			
			(def prodTempMap (sorted-map (Integer/parseInt keyID) valueStr))
			(def prodMap (merge prodMap prodTempMap))
			
			)prodList)
		)
	)
	(def prodDispMap prodMap)
)
;;--------------------------------------------------------------

(defn salesReader []
	(def salesMap(sorted-map))
	
	(def strFile (slurp "sales.txt"))
	(def salesList (clojure.string/split-lines strFile))  
	
	(def custerID( apply str(map (fn [record] 
			(def keyID (nth (split record #"\|" 2) 0) )
			(def valueStr (clojure.string/join ["|" (nth (split record #"\|" 2 ) 1)]))
			
			(def salesTempMap (sorted-map (Integer/parseInt keyID) valueStr))
			(def salesMap (merge salesMap salesTempMap))
			
			)salesList)
		)
	)
	(def salesDispMap salesMap)
)

;;--------------------------------------------------------------
(defn acceptInput []
(println "
*** Sales Menu ***
------------------
1. Display Customer Table
2. Display Product Table
3. Display Sales Table
4. Total Sales for Customer
5. Total Count for Product
6. Exit
Enter an option? :")

	(def input (read-line))
	;; call dispatch
	(cond 
		(= input "1")	((custPrinter)(acceptInput))
		(= input "2")	((prodPrinter) (acceptInput))
		(= input "3")	((salesPrinter) (acceptInput))
		(= input "4")	((custTotalSale) (acceptInput))
		(= input "5")	((prodTotalSaleCount) (acceptInput))
		(= input "6")	((println "Good Bye")(println)(System/exit 0))
		:else ((println "Please select an option from given list.")(acceptInput))
	)
)
;;--------------------------------------------------------------
(defn main []

	;; call to 3 functions to load data before any operation is performed
	(custReader)
	(prodReader)
	(salesReader)

	(acceptInput)
)
(main)
