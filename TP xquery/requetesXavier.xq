(:---------------- Question 1.1 -----------------:)
(:for $f in doc("refbib.xml")/bib/livre
	where $f/titre="Data on the Web"
	return
		for $a in $f//auteur
			return ($a/prenom,$a/nom)
<prenom>Serge</prenom>
<nom>Abiteboul</nom>
<prenom>Peter</prenom>
<nom>Buneman</nom>
<prenom>Dan</prenom>
<nom>Suciu</nom>
:)

(:---------------- Question 1.2 -----------------:)
(:
<auteurs>{
	for $f in doc("refbib.xml")/bib/livre/auteur
	return
		<nom>
			{$f/nom/text(), $f/prenom/text()}
		</nom>	
}</auteurs>
:)


(:---------------- Question 1.3 -----------------:)
(:<biblio>{
	for $l in doc("refbib.xml")/bib/livre
	where $l/edition="Addison-Wesley"
	return
		<livre annee="{$l/@annee}">
			{$l/titre}
		</livre>
}</biblio>:)

(:---------------- Question 1.4 -----------------:)
(:<biblio>{
	for $l in doc("refbib.xml")/bib/livre
	order by $l/titre
	return $l
}</biblio>:)

(:---------------- Question 1.5 -----------------:)
(:for $a in distinct-values(doc("refbib.xml")/bib/livre/auteur/nom/text())
	return 
		<auteur nom="{$a}">
		{
			for $l in doc("refbib.xml")/bib/livre
			where $l/auteur/nom=$a
			return $l/titre
		}
		</auteur>
:)


(:---------------- Question 1.6 -----------------:)
(:<resultats>{
	for $t in distinct-values(doc("refbib.xml")/bib/livre/titre/text())
		return <prixmin titre ="{$t}"><prix>{
			min(for $f in doc("refbib.xml")/bib/livre
				where $f/titre/text() = $t
					return $f/prix)
		}</prix></prixmin>

}</resultats>:)




(:---------------- Question 1.7 -----------------:)
(:for $t in distinct-values(doc("refbib.xml")/bib/livre/titre/text())
let $a:= doc("refbib.xml")/bib/livre[titre/text()=$t]/auteur
	return
	<livre>{
		if (count($a) > 0)
		then( $a[1],
			if (count($a) > 1)
			then( $a[2],
				if (count($a) > 2)
				then( <et_al></et_al>
				)else()
			)else()
		)else()
	}</livre>
:)


(:---------------- Question 2.1 -----------------:)
(:<objects>
{
	for $u in doc("personnes.xml")/personnes/perso_tuple,
		$o in doc("objets.xml")/objets/obj_tuple
	where $u/idperso=$o/propose_par and
		  $u/confiance > "B" and
		  $o/prix_de_reserve > 1000
	return
		$o
	
}
</objects>:)

(:---------------- Question 2.2 -----------------:)
(:<vendeurs>
{
	for $v in doc("personnes.xml")/personnes/perso_tuple
	return
		<vendeur nom="{$v/nom/text()}">
		{
			for $o in doc("objets.xml")/objets/obj_tuple
			where $v/idperso=$o/propose_par
			return
				$o
		}
		</vendeur>
}
</vendeurs>:)


(:---------------- Question 2.3 -----------------:)
(:<velos>
{
	for $v in doc("objets.xml")/objets/obj_tuple
	where contains($v/description/text(), "velo") or contains($v/description/text(), "Velo")
	return
		$v
}
</velos>:)


(:---------------- Question 2.4 -----------------:)
(:<velos>
{
	for $v in doc("objets.xml")/objets/obj_tuple
	let $nil := doc("encheres.xml")/encheres/ench_tuple[noobj/text()=$v/noobj/text()]
	where contains($v/description/text(), "velo") or contains($v/description/text(), "Velo")
	return
		<velo titre="{$v/description/text()}">
			<N>{count($nil)}</N>
			{if (count($nil)>0)
			 then(
			 	<max>{max($nil/ench)}</max>
			 )else()}	
			
		</velo>
}
</velos>:)


(:---------------- Question 2.5 -----------------:)
(:<articles>
{
	for $o in doc("objets.xml")/objets/obj_tuple
	let $e := doc("encheres.xml")/encheres/ench_tuple[noobj/text() = $o/noobj/text() and ench/text() > $o/prix_de_reserve]
	where count($e) > 0
	return 
		$o
}
</articles>:)


(:---------------- Question 2.6 -----------------:)
(:<personnes>
{
	for $p in doc("personnes.xml")/personnes/perso_tuple
	let $e := doc("encheres.xml")/encheres/ench_tuple[idperso/text() = $p/idperso/text() and not(/idperso/text() = following::idperso/text())]
	let $o := doc("objets.xml")/objets/obj_tuple
	where count($e) = count($o)
	return
		$p
}
</personnes>:)

(:---------------- Question 2.7 -----------------:)
<encheres>
{
	for $o in doc("objets.xml")/objets/obj_tuple/noobj
	let $e := doc("encheres.xml")/encheres/ench_tuple[noobj/text() = $o/text()]/ench
	order by avg($e) 
	return
		if(count($e) > 2)
		then (
			<article id="{$o/text()}">
				{avg($e)}	
			</article>
		)else()
	
}
</encheres>

