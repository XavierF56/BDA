<listeClients>
{
	for $c in doc("SQLclient")/res/tuple
	return		
		<client>
			{$c/nom, $c/adresse}
		</client>		
}
</listeClients>
