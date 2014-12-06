<auteurs>
{
	for $f in doc("XMLrefbib")/bib/livre/auteur
	return
		<nom>
			{$f/nom/text(), $f/prenom/text()}
		</nom>	
}
</auteurs>

