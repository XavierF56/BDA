   <fournisseursAlcool2014>
   {
      for $f in doc("SQLfournisseur")/tuple
      let $count :=(for $a in doc("XMLalcool")//alcool[annee < 2007]
      				where $f/id = $a/fournisseur
      				return count($a))
      return if(0 < $count)then($f)else()
   }
   </fournisseursAlcool2014>
