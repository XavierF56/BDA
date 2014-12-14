<fournisseursAlcool2014>
{
    for $a in doc("XMLalcool")//alcool
  	where $a//annee = "2014"
  	return
        for $f in doc("SQLfournisseur")/tuple
     	where $f/id = $a/fournisseur
     	return $f	
}
</fournisseursAlcool2014>
