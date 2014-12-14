<barsStevenSeagal>
{
	for $c in doc("SQLclient")/tuple
	where $c/nom = "Steven Seagal"
	return count(
		for $b in doc("XMLbar")//bar
		where $b/alcool = $c/alcool_prefere
		return $b
	)
}
</barsStevenSeagal>
