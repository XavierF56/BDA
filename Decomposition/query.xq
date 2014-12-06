<auteurs>
{
	for $f in doc("fournisseur")//nom
	return
		<nom>
			{$f/text()}
		</nom>	
}
</auteurs>

