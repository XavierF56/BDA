<auteurs>
{
	for $f in doc("refbib.xml")/bib/livre/auteur
	return
		<nom>
			{$f/nom/text(), $f/prenom/text()}
		</nom>	
}
</auteurs>
