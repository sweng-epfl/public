# DP - UI -- Ex7 - Solutions

The problem with this approach is that if you want to add a new conversion (e.g., conversion to LaTeX), you will have to modify the implementation of `Document`, `DocumentPart` and the classes implementing them.
One solution is to use the Visitor Pattern. Indeed, the Visitor Pattern allows you to add new features to a group of classes without modifying them.