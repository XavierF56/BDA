<barsAlcool2014>
{
	for $r in distinct-values(
		for $b in doc("XMLbar")//bar,
			$a in doc("XMLalcool")//alcool
		where $b/alcool = $a/@id
			and $a//annee = "2014"
		return 
			$b/@nom 
		)
	return <bar>{$r}</bar>
}
</barsAlcool2014>
