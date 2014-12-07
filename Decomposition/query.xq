<auteurs>
{
	for $i in doc("fournisseur")//id
	return
		
		for $a in doc("XMLalcool")//alcool
		where $a/fournisseur/text() = $i/text()
		return
			<alcool>
				{$i, $a/text()}
			</alcool>
		
}
</auteurs>

